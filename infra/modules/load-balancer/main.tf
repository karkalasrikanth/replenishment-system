resource "aws_lb" "finance_alb" {
  name               = "finance-alb"
  internal           = false
  load_balancer_type = "application"

  # MUST be 2 subnets in 2 AZs
  subnets         = var.public_subnets
  security_groups = [var.alb_sg]

  enable_deletion_protection = false

  tags = {
    Name = "finance-alb"
  }
}

# -------------------------
# TARGET GROUP - API
# -------------------------
resource "aws_lb_target_group" "finance_api_tg" {
  name        = "finance-api-tg"
  port        = 8080
  protocol    = "HTTP"
  vpc_id      = var.vpc_id
  target_type = "ip"

  health_check {
    path                = "/actuator/health"
    healthy_threshold   = 2
    unhealthy_threshold = 3
    interval            = 30
    timeout             = 5
    matcher             = "200"
  }
}

# -------------------------
# TARGET GROUP - UI
# -------------------------
resource "aws_lb_target_group" "finance_ui_tg" {
  name        = "finance-ui-tg"
  port        = 80
  protocol    = "HTTP"
  vpc_id      = var.vpc_id
  target_type = "ip"

  health_check {
    path                = "/"
    healthy_threshold   = 2
    unhealthy_threshold = 3
    interval            = 30
    timeout             = 5
    matcher             = "200"
  }
}

# -------------------------
# LISTENER (DEFAULT 404)
# -------------------------
resource "aws_lb_listener" "http" {
  load_balancer_arn = aws_lb.finance_alb.arn
  port              = 80
  protocol          = "HTTP"

  default_action {
    type = "fixed-response"

    fixed_response {
      content_type = "text/plain"
      message_body = "Not Found"
      status_code  = "404"
    }
  }
}

# -------------------------
# ROUTE /api → API TG
# -------------------------
resource "aws_lb_listener_rule" "api_rule" {
  listener_arn = aws_lb_listener.http.arn
  priority     = 10

  condition {
    path_pattern {
      values = ["/api/*"]
    }
  }

  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.finance_api_tg.arn
  }
}

# -------------------------
# ROUTE /* → UI TG
# -------------------------
resource "aws_lb_listener_rule" "ui_rule" {
  listener_arn = aws_lb_listener.http.arn
  priority     = 20

  condition {
    path_pattern {
      values = ["/*"]
    }
  }

  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.finance_ui_tg.arn
  }
}
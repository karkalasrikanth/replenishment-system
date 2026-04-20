resource "aws_ecs_cluster" "finance_cluster" {
  name = "finance-cluster"
}

resource "aws_ecs_task_definition" "finance_service" {
  family                   = "finance-service"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = "512"
  memory                   = "1024"
  execution_role_arn       = var.execution_role_arn
  container_definitions = jsonencode([
    {
      name  = "finance-service"
      image = var.backend_image

      portMappings = [
        {
          containerPort = 8080
        }
      ]
      environment = [
        {
          name      = "DB_URL"
          value = var.db_host_name
        },
        {
          name      = "DB_USERNAME"
          value = var.db_username
        },
        {
          name      = "DB_PASSWORD"
          value = var.db_password
        }
      ]
    }
  ])
}

resource "aws_ecs_service" "finance_service" {
  name            = "finance-service"
  cluster         = aws_ecs_cluster.finance_cluster.arn
  task_definition = aws_ecs_task_definition.finance_service.arn
  launch_type     = "FARGATE"
  desired_count   = var.desired_count
  network_configuration {
    subnets          = [var.subnet_id]
    security_groups  = [var.security_group_id]
    assign_public_ip = true
  }

  load_balancer {
    target_group_arn = var.finance_api_tg_arn
    container_name   = "finance-service"
    container_port   = 8080
  }
}

resource "aws_ecs_task_definition" "finance_ui" {
  family                   = "finance-ui"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = "256"
  memory                   = "512"
  execution_role_arn       = var.execution_role_arn
  container_definitions = jsonencode([
    {
      name  = "finance-ui"
      image = var.ui_image

      portMappings = [
        {
          containerPort = 80
        }
      ]
    }
  ])
}

resource "aws_ecs_service" "finance_ui" {
  name            = "finance-ui"
  cluster         = aws_ecs_cluster.finance_cluster.arn
  task_definition = aws_ecs_task_definition.finance_ui.arn
  launch_type     = "FARGATE"
  desired_count   = var.desired_count
  network_configuration {
    subnets          = [var.subnet_id]
    security_groups  = [var.security_group_id]
    assign_public_ip = true
  }
  load_balancer {
    target_group_arn = var.finance_ui_tg_arn
    container_name   = "finance-ui"
    container_port   = 80
  }
}
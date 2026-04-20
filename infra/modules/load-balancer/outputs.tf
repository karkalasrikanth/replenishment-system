output "finance_api_tg_arn" {
  value = aws_lb_target_group.finance_api_tg.arn
}

output "finance_ui_tg_arn" {
  value = aws_lb_target_group.finance_ui_tg.arn
}
variable "subnet_id" {
  type = string
}

variable "security_group_id" {
  type = string
}

variable "desired_count" {
  type    = number
  default = 1
}

variable "db_username" {
  type      = string
  sensitive = true
}
variable "db_password" {
  type      = string
  sensitive = true
}
variable "db_host_name" {}

variable "execution_role_arn" {
  default = ""
}

variable "backend_image" {
  default = "finance-app:latest"
}

variable "ui_image" {
  default = "finance-ui:latest"
}

variable "finance_api_tg_arn" {
  default = ""
}

variable "finance_ui_tg_arn" {
  default = ""
}
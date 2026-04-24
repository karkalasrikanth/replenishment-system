variable "alb_sg" {}

variable "public_subnets" {
  type = list(string)
}

variable "vpc_id" {}
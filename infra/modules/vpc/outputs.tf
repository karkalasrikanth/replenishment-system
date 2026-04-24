output "vpc_id" {
  value = aws_vpc.main.id
}

output "private_subnet_id" {
  value = aws_subnet.private_subnet.id
}

output "ecs_sg_id" {
  value = aws_security_group.ecs_sg.id
}

output "public_subnet_id" {
  value = aws_subnet.public_subnet.id
}

output "alb_sg" {
  value = aws_security_group.alb_sg.id
}
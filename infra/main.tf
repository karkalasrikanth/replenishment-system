module "vpc" {
  source = "./modules/vpc"
}

module "rds" {
  source      = "./modules/rds"
  db_password = var.db_password
  db_username = var.db_username
}

module "ecr" {
  source = "./modules/ecr"
}

module "load-balancer" {
  source = "./modules/load-balancer"
}
module "ecs" {
  source             = "./modules/ecs"
  subnet_id          = module.vpc.private_subnet_id
  security_group_id  = module.vpc.ecs_sg_id
  db_username        = var.db_username
  db_password        = var.db_password
  db_host_name       = module.rds.db_endpoint
  finance_api_tg_arn = module.load-balancer.finance_api_tg_arn
  finance_ui_tg_arn  = module.load-balancer.finance_ui_tg_arn
}
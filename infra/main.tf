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
  vpc_id = module.vpc.vpc_id
  alb_sg = module.vpc.alb_sg
  public_subnets = [
    module.vpc.public_subnet_1_id,
    module.vpc.public_subnet_2_id

  ]
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
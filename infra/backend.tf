terraform {
  backend "s3" {
    bucket         = "tf-state-552144477875-us-east-2-an"
    key            = "finance/terraform.tfstate"
    region         = "us-east-1"
    dynamodb_table = "tf-locks"
    encrypt        = true
  }
}
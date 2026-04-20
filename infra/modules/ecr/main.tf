resource "aws_ecr_repository" "finance_tracker" {
  name                 = "finance-tracker"
  image_tag_mutability = "MUTABLE"
  image_scanning_configuration {
    scan_on_push = true
  }
}

resource "aws_ecr_lifecycle_policy" "finance_tracker_lifecycle" {
  repository = aws_ecr_repository.finance_tracker.name

  policy = jsonencode({
    rules = [
      {
        rulePriority = 1
        description  = "Keep only last 20 images"
        selection = {
          tagStatus   = "any"
          countType   = "imageCountMoreThan"
          countNumber = 20
        }
        action = {
          type = "expire"
        }
      }
    ]
  })
}
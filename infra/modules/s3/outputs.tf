output "cloudfront_url" {
  value = aws_cloudfront_distribution.ui.domain_name
}
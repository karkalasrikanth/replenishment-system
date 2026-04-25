FROM nginx:alpine

COPY nginx.conf /etc/nginx/conf.d/default.conf

# Angular dist injected from CI
COPY dist/finance-ui /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]

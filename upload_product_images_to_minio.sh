# curl "https://awscli.amazonaws.com/AWSCLIV2.pkg" -o "./aws-cli.pkg"

# sudo installer -pkg /Users/stradiavanti/Downloads/aws-cli.pkg -target /

aws --endpoint-url http://localhost:9000 s3 ls

aws --endpoint-url http://localhost:9000 s3 cp /Users/stradiavanti/Downloads/products s3://bootcups/products --recursive --exclude .DS_Store
rm -rf /var/lib/apt/lists/* && \
rm /etc/nginx/sites-enabled/default && \
service nginx start
(cd /goormService/frontend && npm i && npm start)
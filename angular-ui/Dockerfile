# IMAGE
FROM node:20

# COPY APP
RUN npm install -g @angular/cli
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .

# CONFIG SERVER
EXPOSE 4000
ENV CHOKIDAR_USEPOLLING=true
CMD ["ng", "serve", "--host", "0.0.0.0", "--port", "4000", "--poll", "1000"]
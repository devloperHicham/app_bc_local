# Step 1: Build Angular app
FROM node:23-slim AS build

WORKDIR /app
COPY package*.json ./
RUN npm install -g @angular/cli && npm install

# Copy app files
COPY . .
RUN ng build --configuration production

# Step 2: Serve with Nginx
FROM nginx:alpine

# Clean default content
RUN rm -rf /usr/share/nginx/html/*

# Copy built Angular files
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

# Copy Nginx config
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 4000

CMD ["nginx", "-g", "daemon off;"]

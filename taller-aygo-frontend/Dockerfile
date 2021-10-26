# Stage 1

FROM node:latest as build
WORKDIR /usr/local/app
COPY ./ /usr/local/app/
RUN npm install
RUN npm run build --prod

# Stage 2

FROM nginx:latest
COPY --from=build /usr/local/app/dist/taller-aygo-frontend /usr/share/nginx/html
EXPOSE 80

# Copy the EntryPoint
COPY ./entryPoint.sh /
RUN chmod +x entryPoint.sh
ENTRYPOINT ["/entryPoint.sh"]
CMD ["nginx", "-g", "daemon off;"]

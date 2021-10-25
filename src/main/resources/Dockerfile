FROM node:alpine

WORKDIR /redis-commander

# Install app dependencies
## Install nodejs on ubuntu ##
RUN wget http://nodejs.org/dist/v0.6.9/node-v0.6.9.tar.gz \
    && tar -xvzf node-v0.6.9.tar.gz \
    && cd node-v0.6.9 \
    && ./configure && make && sudo make install \
    && mkdir myapp && cd myapp \
    && npm init \
    && npm install express --save \
    && npm install express \
    && npm install --save path serve-favicon morgan cookie-parser body-parser \
    && npm install --save express jade \
    && npm install --save debug \

COPY package.json /usr/src/app/
RUN npm install

# Bundle app source
COPY . /usr/src/app

CMD ["npm", "start" ]

EXPOSE 8081

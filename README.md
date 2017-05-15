# Install

```
cd Chat-Application-241/chat_app_be
npm install

cd ../chat_app_fe
npm install
npm run bulid
```

# Run

In order for the backend to work you have to have mongodb installed on your machine.

[Installation instructions for mongodb can be found here.](https://docs.mongodb.com/master/administration/install-community/)

You also need to have gulp installed on your machine.

```
npm install -g gulp
```

```
cd chat_app_be
gulp
```

Now your server should be listening for requests. You should be able to access <http://localhost:8000/> and use the application.

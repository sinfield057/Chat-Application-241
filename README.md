
# Chat-Application-241
Aplicatie pentru chat rooms.
Grupa 241

# Dependinte

MongoDB ca baza de date
https://www.mongodb.com/download-center?jmp=nav

Robomongo pentru a edita date fara linie de comanda
https://robomongo.org/download

Node.js si NPM
https://www.npmjs.com/get-npm?utm_source=house&utm_medium=homepage&utm_campaign=free%20orgs&utm_term=Install%20npm


# Setare proiect

`cd /(path_catre_proiect)/chat_app_be`

`npm install`

`cd ../chat_app_fe`

`npm install`

# Rulare
Proiectul merge prin gulp care este deja setat:

`cd /(path_catre_proiect)/chat_app_be`

`gulp`

Pentru Front-end, pentru a rula serverul de test:

`cd /(path_catre_proiect)/chat_app_fe`

`npm run dev`

Call-urile de API nu o sa functioneze pe serverul de dev, dar se poate construi direct pentru productie si rula de pe serverul de Node.js

`npm run build`

Este configurat sa copieze automat fisierele necesare in /chat_app_be/public/ prin Webpack

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


db = db.getSiblingDB('rinha');

db.createCollection('user_account');

db.user_account.insertMany([
    { _id: 1, saldo: 0 , limite: 100000 },
    { _id: 2, saldo: 0 , limite: 80000 },
    { _id: 3, saldo: 0 , limite: 1000000 },
    { _id: 4, saldo: 0 , limite: 10000000 },
    { _id: 5, saldo: 0 , limite: 500000 }
]);

db.createCollection('transactions');
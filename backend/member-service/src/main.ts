import env from 'dotenv';
import {Db, MongoClient} from "mongodb";
import express, {json} from 'express';
import {router as MemberRouter} from './api/member-rest-controller';
import cors from "cors";

env.config();
export let datasource: Db;

async function main(){
    const mongoClient = new MongoClient(process.env.APP_DB_URL!);
    await mongoClient.connect();
    console.log("Successfully connected with the MongoDB Server");
    datasource =  mongoClient.db(process.env.APP_DB_NAME)
}

main().then(value => {
    const app = express();

    const corsOptions = {
        origin: 'http://localhost:4200',
        methods: 'GET,HEAD,PUT,PATCH,POST,DELETE',
        credentials: true,  // Enable credentials (e.g., cookies) for cross-origin requests
    };

    app.use(cors(corsOptions));
    app.use(json());
    app.use("/api/v1/members", MemberRouter);

    app.listen(process.env.APP_PORT,
        ()=> console.log(`Server has been created at ${process.env.APP_PORT}`))
}).catch(console.error);
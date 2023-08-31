import express, {RequestHandler} from 'express';
import cors from 'cors';
import {
    deleteMemberById, existsMemberByContactNotId,
    existsMemberById,
    findMembers,
    getMemberById,
    saveMember,
    updateMember
} from "../repository/member-repository";
import {Member} from "../dto/member";
import Joi, {string, ValidationError} from "joi";
import {AxiosResponse} from "axios";

export const router = express.Router();
router.use(cors());
const axios = require('axios');


const memberValidator:RequestHandler = (req, res, next) =>{
    const member = req.body as Member;
    try {
        Joi.assert(member, Member.SCHEMA, {abortEarly:false});
        next();
    } catch (e){
        if(e instanceof ValidationError) res.status(400).json(e.details);
    }
}


router.post('/',memberValidator, async (req, res) => {
    const member = req.body as Member;

    if (await existsMemberById(member._id)){
        res.status(409).send("NIC already exists");
        return;
    }
    if (await existsMemberByContactNotId(member.contact, member._id)){
        res.status(409).send("Contact number already associated with another member");
        return;
    }

    await saveMember(req.body);
    res.sendStatus(204);
});


router.patch('/:memberId', memberValidator, async (req, res) => {
    const member = req.body as Member;
    member._id = req.params['memberId'];

    if (!await existsMemberById(member._id)){
        res.status(404).send("Member doesn't exist");
        return;
    }
    if (await existsMemberByContactNotId(member.contact, member._id)){
        res.status(409).send("Contact number already associated with another member");
        return;
    }

    await updateMember(member);
    res.sendStatus(204);
});


router.delete('/:memberId', async (req, res) => {

    if (!await existsMemberById(req.params.memberId)){
        res.status(404).send("Member doesn't exist");
        return;
    }

    let result: string = "";

    await axios.get("http://localhost:8080/api/v1/issues/member/" + req.params.memberId)
        .then((response: AxiosResponse<string>) => {
            const responseData = response.data;
            if (responseData === "Yes"){
                result = "Yes";
                res.status(409).send("The member has already received a book");
                return;
            }
        })
        .catch(function (error:any) {
            res.status(400).send("Something went wrong");
            return;
        })
    if(result !== "Yes"){
        await deleteMemberById(req.params.memberId);
        res.sendStatus(204);
    }
});


router.get('/:memberId', async (req, res) => {
    const member = await getMemberById(req.params.memberId);

    if (member){
        res.json(member);
    }else{
        res.status(404).send("Member doesn't exist");
    }
});


router.get('/', async (req, res) => {
    const query = req.query.q ?? '';
    const memberList = await findMembers(query as string);
    res.json(memberList);
});
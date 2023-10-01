export class Issue {

  constructor(public id:number|null, public isbn:string, public memberId:string, public issueDate:Date,
              public dueDate:Date, public fine:number, public retuned:string) {
  }
}

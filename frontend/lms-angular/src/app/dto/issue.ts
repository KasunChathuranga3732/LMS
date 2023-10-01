export class Issue {

  constructor(public id:number|null, public isbn:string, public memberId:string, public issueDate:Date,
              public returnDate:Date, public fine:number, public returned:string) {
    this.fine = parseFloat(this.fine.toFixed(2));
  }
}

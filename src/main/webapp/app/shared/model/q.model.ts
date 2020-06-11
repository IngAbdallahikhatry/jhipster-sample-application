export interface IQ {
  id?: number;
  countryName?: string;
}

export class Q implements IQ {
  constructor(public id?: number, public countryName?: string) {}
}

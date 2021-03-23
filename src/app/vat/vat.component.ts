import { Component, Input, OnInit } from '@angular/core';
import { Currency } from '../models/currency';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-vat',
  templateUrl: './vat.component.html',
  styleUrls: ['./vat.component.less']
})
export class VatComponent implements OnInit {

  private vatRate = Number(`${environment.vatRate}`);
  vat: string;

  constructor() { }

  ngOnInit(): void {
  }

  @Input('amount')
  set updateVAT(amount) {
    let vatAmount: any;
    let grossAmount: number;
    let currency: string;
    const regExp = /[^\d\.]*$/g;

    if (amount) {
      grossAmount = Number(amount.replace(regExp, ''));
      currency = amount.match(regExp).join('').trim();

      if (!isNaN(grossAmount) && this.isSupportedCurrency(currency)) {
        vatAmount = grossAmount * (this.vatRate / 100);
        vatAmount = vatAmount.toFixed(2);

        vatAmount = (currency) ? vatAmount + ' ' + currency : vatAmount;
      }
    }
    this.vat = vatAmount;
  }

  private isSupportedCurrency(currency): boolean {
    return currency === '' || Object.values(Currency).indexOf(currency) !== -1;
  }
}

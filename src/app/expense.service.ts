import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Expense } from './expenses/expense';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {

  private expensesUrl = '/app/expenses';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getExpenses(): Observable<Expense[]> {
    return this.http.get<Expense[]>(this.expensesUrl);
  }

  createExpense(expense: Expense): Observable<Expense> {
    return this.http.post<Expense>(this.expensesUrl, expense, this.httpOptions);
  }
}

import { Component, OnInit } from '@angular/core';
import { Expense } from './expense';
import { ExpenseService } from '../expense.service';

@Component({
  selector: 'app-expenses',
  templateUrl: './expenses.component.html',
  styleUrls: ['./expenses.component.less']
})
export class ExpensesComponent implements OnInit {

  newExpense: Expense = {
  };

  expenses: Expense[];

  constructor(private expenseService: ExpenseService) { }

  ngOnInit(): void {
    this.loadExpenses();
  }

  private loadExpenses(): void {
    this.expenseService.getExpenses().subscribe((expenses) => {
      this.expenses = expenses;
    });
  }

  saveExpense(): void {
    this.expenseService.createExpense(this.newExpense).subscribe(() => {
      this.clearExpense();
      this.loadExpenses();
    });
  }

  clearExpense(): void {
    this.newExpense = {};
  }

}

import { Component, OnInit } from '@angular/core';
import { Expense } from './expense';
import { ExpenseService } from '../expense.service';
import { NotificationService } from '../notification.service';

@Component({
  selector: 'app-expenses',
  templateUrl: './expenses.component.html',
  styleUrls: ['./expenses.component.less']
})
export class ExpensesComponent implements OnInit {

  newExpense: Expense = {
  };

  expenses: Expense[];

  constructor(
    private expenseService: ExpenseService,
    private notificationService: NotificationService) { }

  ngOnInit(): void {
    this.loadExpenses();
  }

  private loadExpenses(): void {
    this.expenseService.getExpenses().subscribe((expenses) => {
      this.expenses = expenses;
    });
  }

  saveExpense(): void {
    this.notificationService.clear();

    this.expenseService.createExpense(this.newExpense)
    .subscribe(() => {
      this.notificationService.success('Expense successfully created', {autoClose: true});
      this.clearExpense();
      this.loadExpenses();
    }, (error: any) => {
      if (Array.isArray(error)) {
        error.forEach(e => this.notificationService.error(e, {autoClose: false}));
      } else {
        this.notificationService.error(error, {autoClose: false});
      }
    });
  }

  clearExpense(): void {
    this.newExpense = {};
  }
}

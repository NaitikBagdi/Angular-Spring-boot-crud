import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomerTableComponent } from './customer-table/customer-table.component';

const routes: Routes = [
  { path: 'spring-angular-crud', component: CustomerTableComponent },
  { path: '', redirectTo: '/spring-angular-crud', pathMatch: "full" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

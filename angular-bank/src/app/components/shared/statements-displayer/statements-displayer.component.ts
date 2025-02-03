import { Component, Input } from '@angular/core';
import { Statement } from '../../../models/statement';
import { DatePipe, DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-statements-displayer',
  imports: [DatePipe, DecimalPipe],
  templateUrl: './statements-displayer.component.html',
  styleUrl: './statements-displayer.component.css',
})
export class StatementsDisplayerComponent {
  @Input() public statements!: Statement[];
}

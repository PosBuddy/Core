import {Component, inject, TemplateRef} from '@angular/core';
import {DecimalPipe} from "@angular/common";
import {NgbAlert, NgbOffcanvas} from "@ng-bootstrap/ng-bootstrap";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ZXingScannerModule} from "@zxing/ngx-scanner";
import {dispensingStation, item, paymentService} from "../service/payment.service";

@Component({
  selector: 'app-serve',
  standalone: true,
  imports: [
    DecimalPipe,
    NgbAlert,
    ReactiveFormsModule,
    ZXingScannerModule,
    FormsModule,
    DecimalPipe
  ],
  templateUrl: './serve.component.html',
  styleUrl: './serve.component.css'
})
export class ServeComponent {
  constructor(private paymentService: paymentService) {

  }

  private offcanvasService = inject(NgbOffcanvas);

  serverResponse: string = "-"
  confirmError: boolean = false;
  confirmOK: boolean = false;
  formValid: boolean = false;
  formValidText: string = "ID zur Anzeige der Umsätze scannen"
  posBuddyId: string = "-";
  balance = '0';

  items: Array<item> = [];
  dispensingStations: Array<dispensingStation> = [];

  ngAfterViewInit() {
    console.log("get items and dispensing stations")
    this.paymentService
      .getDispensingStations()
      .subscribe(dispensingStations => {
          this.dispensingStations = dispensingStations;
        }, err => {
          this.confirmError = true;
          this.serverResponse = "Fehler bei laden der Ausgabestationen"
        }
      )


  }

  scanQRCode(content: TemplateRef<any>) {
    this.offcanvasService.open(content, {ariaLabelledBy: 'scanId'})
  }

  onScanSuccess(scanResult: string) {
    this.posBuddyId = scanResult;
    this.formValid = true
    this.offcanvasService.dismiss("success");
  }


  resetError() {
    this.confirmError = false;
    this.serverResponse = "-";
    this.posBuddyId = "-";
  }

  resetOK() {
    this.confirmOK = false;
    this.serverResponse = "-";
    this.posBuddyId = "-";
  }


}

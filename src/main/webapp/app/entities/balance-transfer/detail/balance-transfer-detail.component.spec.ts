import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BalanceTransferDetailComponent } from './balance-transfer-detail.component';

describe('Component Tests', () => {
  describe('BalanceTransfer Management Detail Component', () => {
    let comp: BalanceTransferDetailComponent;
    let fixture: ComponentFixture<BalanceTransferDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [BalanceTransferDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ balanceTransfer: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(BalanceTransferDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BalanceTransferDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load balanceTransfer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.balanceTransfer).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});

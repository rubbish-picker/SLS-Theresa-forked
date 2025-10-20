package Theresa.action;

import Theresa.patch.DustPatch;
import Theresa.patch.OtherEnum;
import Theresa.patch.SilkPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class TriggerDustSilkAction extends AbstractGameAction {
    public TriggerDustSilkAction() {}

    @Override
    public void update() {
        for(AbstractCard c : DustPatch.dustManager.dustCards){
            SilkPatch.triggerSilk(SilkPatch.TriggerType.ALL,c, OtherEnum.Theresa_Dust);
        }
        this.isDone = true;
    }
}

package Theresa.action;

import Theresa.power.debuff.DyingBurstPower;
import Theresa.power.debuff.DyingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class DyingBurstAction extends AbstractGameAction {
    public DyingBurstAction(AbstractCreature source, AbstractCreature target) {
        this.target = target;
        this.source = source;
    }

    @Override
    public void update() {
        AbstractPower dying = this.target.getPower(DyingPower.POWER_ID);
        if(dying instanceof DyingPower) {
            if(((DyingPower) dying).reachMax()){
                addToTop(new ApplyPowerAction(target,source,new DyingBurstPower(target,1),1));
                addToTop(new ApplyPowerAction(target,source,new StrengthPower(target,-1),-1));
            }
            addToTop(new LoseHPAction(target,source,dying.amount));
            addToTop(new RemoveSpecificPowerAction(target,source,dying));
        }

        this.isDone = true;
    }
}

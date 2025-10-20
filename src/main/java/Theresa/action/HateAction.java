package Theresa.action;

import Theresa.power.buff.HatePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HateAction extends AbstractGameAction {
    public HateAction(HatePower power,int amt) {
        this.h = power;
        this.amount = amt;
    }

    @Override
    public void update() {
        if(h.owner!=null && !h.owner.isDeadOrEscaped()) {
            addToTop(new DamageAction(AbstractDungeon.player,new DamageInfo(h.owner,this.amount, DamageInfo.DamageType.THORNS),AttackEffect.FIRE,true));
        }

        this.isDone = true;
    }

    HatePower h;
}

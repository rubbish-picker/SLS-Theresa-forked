package Theresa.action;

import Theresa.power.debuff.DyingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DyingAction extends AbstractGameAction {
    public DyingAction(DyingPower power) {
        this.p = power;
    }

    DyingPower p;

    public void update() {
        p.waitDyingActionDone = false;
        p.owner.currentHealth = 0;
        p.owner.healthBarUpdatedEvent();
        p.owner.damage(new DamageInfo((AbstractCreature)null, 0, DamageInfo.DamageType.HP_LOSS));
        this.isDone = true;
    }
}


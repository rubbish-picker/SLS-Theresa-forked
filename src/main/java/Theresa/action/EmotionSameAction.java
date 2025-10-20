package Theresa.action;

import Theresa.power.buff.HatePower;
import Theresa.power.buff.HopePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EmotionSameAction extends AbstractGameAction {
    public EmotionSameAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        for(AbstractMonster m: AbstractDungeon.getMonsters().monsters) {
            if(!m.isDeadOrEscaped()) {
                if(m.hasPower(HatePower.POWER_ID)) {
                    int tmpAmount = Math.min(amount, m.getPower(HatePower.POWER_ID).amount);
                    if(tmpAmount > 0) {
                        addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new HatePower(AbstractDungeon.player,tmpAmount),tmpAmount));
                        addToTop(new ReducePowerAction(m,m,HatePower.POWER_ID,tmpAmount));
                    }
                }
                else {
                    addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new HopePower(AbstractDungeon.player,amount),amount));
                }
            }
        }

        this.isDone = true;
    }
}

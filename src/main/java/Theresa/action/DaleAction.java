package Theresa.action;

import Theresa.power.buff.HatePower;
import Theresa.power.buff.HopePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DaleAction extends AbstractGameAction {
    public DaleAction(int[] multiDamage, DamageInfo.DamageType type, AttackEffect effect) {
        this.multiDamage = multiDamage;
        this.damageType = type;
        this.attackEffect = effect;
    }

    int[] multiDamage;

    @Override
    public void update() {
        AbstractPower hate = AbstractDungeon.player.getPower(HatePower.POWER_ID);
        AbstractPower hope = AbstractDungeon.player.getPower(HopePower.POWER_ID);
        int hateAmount = 0;
        int hopeAmount = 0;
        if(hate!=null)
            hateAmount = hate.amount;
        if(hope!=null)
            hopeAmount = hope.amount;
        int powerAmount = Math.abs(hateAmount-hopeAmount);
        if(powerAmount>6)
            powerAmount = 6;
        AbstractPower powerToObtain = null;
        if(hateAmount>hopeAmount){
            powerToObtain = new HopePower(AbstractDungeon.player,powerAmount);
        }
        else if(hateAmount<hopeAmount){
            powerToObtain = new HatePower(AbstractDungeon.player,powerAmount);
        }

        if(powerAmount>0 && powerToObtain!=null){
            //damage
            for(int i = 0; i < powerAmount; i++){
                addToTop(new DamageAllEnemiesAction(AbstractDungeon.player,multiDamage,damageType,attackEffect));
            }
            //power
            addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,powerToObtain,powerAmount));
        }

        this.isDone = true;
    }
}

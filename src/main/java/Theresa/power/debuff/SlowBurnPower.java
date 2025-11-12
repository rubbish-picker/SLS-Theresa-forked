package Theresa.power.debuff;

import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.SlowPower;

public class SlowBurnPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:SlowBurnPower";

    public SlowBurnPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        setDebuff();
        setAmountDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        AbstractPower p = this.owner.getPower(SlowPower.POWER_ID);
        if(p!=null){
            int amount = p.amount * this.amount;
            addToBot(new ApplyPowerAction(owner, owner, new DyingPower(owner, amount), amount));
            //addToBot(new DamageAction(this.owner, new DamageInfo(null,amount,DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
        else {
            addToBot(new ApplyPowerAction(this.owner,this.owner,new SlowPower(this.owner,0)));
        }
    }
}

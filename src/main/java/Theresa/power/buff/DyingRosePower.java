package Theresa.power.buff;

import Theresa.action.CheckDyingPowerAction;
import Theresa.power.AbstractTheresaPower;
import Theresa.power.debuff.DyingPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class DyingRosePower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:DyingRosePower";

    public DyingRosePower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        setAmountDescription();
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        for(AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if(!mo.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(mo,this.owner,new DyingPower(mo, this.amount),this.amount));
                addToBot(new ApplyPowerAction(mo,this.owner,new VulnerablePower(mo,1,false),1));
                addToBot(new CheckDyingPowerAction(this.owner, mo));
            }
        }
    }
}

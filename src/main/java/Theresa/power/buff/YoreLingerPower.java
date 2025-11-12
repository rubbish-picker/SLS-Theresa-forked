package Theresa.power.buff;

import Theresa.modifier.ForgetMod;
import Theresa.patch.DustPatch;
import Theresa.power.AbstractTheresaPower;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class YoreLingerPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:YoreLingerPower";

    public YoreLingerPower(AbstractCreature owner, int amount) {
        super(POWER_ID,owner,amount);
        setAmountDescription();
        setPriority(-5);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        for(AbstractCard c : DustPatch.dustManager.dustCards) {
            for(int i =0;i<this.amount;i++)
                CardModifierManager.addModifier(c,new ForgetMod());
        }
    }
}

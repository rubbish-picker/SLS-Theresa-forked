package Theresa.power.buff;

import Theresa.action.TimeToDieAction;
import Theresa.power.AbstractTheresaPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TimeToDiePower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:TimeToDiePower";

    public TimeToDiePower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        setAmountDescription();
    }

    @Override
    public void onSpecificTrigger() {
        this.flashWithoutSound();
        addToBot(new TimeToDieAction(amount));
    }

    @Override
    public void onExhaust(AbstractCard card) {
        this.flashWithoutSound();
        addToBot(new TimeToDieAction(amount));
    }

    @SpirePatch(clz = ShowCardAndPoofAction.class,method = SpirePatch.CONSTRUCTOR)
    public static class PoofCardActionPatch{
        public static void Postfix(ShowCardAndPoofAction _inst,AbstractCard card){
            if(AbstractDungeon.player.hasPower(TimeToDiePower.POWER_ID)){
                AbstractDungeon.player.getPower(TimeToDiePower.POWER_ID).onSpecificTrigger();
            }
        }
    }
}

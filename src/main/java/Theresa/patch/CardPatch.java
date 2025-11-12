package Theresa.patch;

import Theresa.action.DelayActionAction;
import Theresa.action.UnfadedAction;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CardPatch {

    //更好的萦绕展示
    @SpirePatch(clz = AbstractCard.class,method = SpirePatch.CLASS)
    public static class LingerField{
        public static SpireField<Boolean> lingerPlayed = new SpireField<Boolean>(()->false);
        public static SpireField<Boolean> isLingeredCard = new SpireField<>(()->false);
    }

    @SpirePatch(clz = AbstractPlayer.class,method = "useCard")
    public static class UseCardPatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer _inst, AbstractCard c, AbstractMonster monster, int energyOnUs){
            if(LingerField.isLingeredCard.get(c)){
                LingerField.isLingeredCard.set(c,false);
                LingerField.lingerPlayed.set(c,true);
                AbstractDungeon.actionManager.addToBottom(new UnlimboAction(c));
                if(!c.purgeOnUse)
                    AbstractDungeon.actionManager.addToBottom(new DelayActionAction(new UnfadedAction(c)));
            }
        }
    }
}

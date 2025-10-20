package Theresa.modifier;

import Theresa.helper.StringHelper;
import Theresa.patch.OtherEnum;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

public class OnlyForYouMod extends AbstractCardModifier {
    public static String ID = "theresa:OnlyForYouModifier";

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL " + StringHelper.TAGS.TEXT[4]+ (Settings.lineBreakViaCharacter ? " " : "") + LocalizedStrings.PERIOD;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(OtherEnum.Theresa_Darkness);
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.tags.remove(OtherEnum.Theresa_Darkness);
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new OnlyForYouMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}

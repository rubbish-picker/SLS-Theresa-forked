package Theresa.modifier;

import Theresa.action.EternalAction;
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

public class EternalMod extends AbstractCardModifier {
    public static String ID = "theresa:EternalMod";

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return StringHelper.TAGS.TEXT[9]+ (Settings.lineBreakViaCharacter ? " " : "") + LocalizedStrings.PERIOD + " NL " + rawDescription;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(OtherEnum.Theresa_Darkness);
        card.tags.add(AbstractCard.CardTags.HEALING);
        card.exhaust = true;
        card.isEthereal = true;
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.tags.remove(OtherEnum.Theresa_Darkness);
        card.tags.remove(AbstractCard.CardTags.HEALING);
        card.exhaust = false;
        card.isEthereal = false;
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return false;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(new EternalAction(card));
    }

    @Override
    public void onExhausted(AbstractCard card) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EternalMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}

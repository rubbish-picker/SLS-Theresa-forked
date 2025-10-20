package Theresa.card.skill;

import Theresa.action.IncreaseMaxDustAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.OtherEnum;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KingInheritance extends AbstractTheresaCard {
    public static final String ID = "theresa:KingInheritance";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public KingInheritance() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        AbstractCard injury = new Injury();
        CardModifierManager.addModifier(injury,new RetainMod());
        this.cardsToPreview = injury;
        this.tags.add(OtherEnum.Theresa_Darkness);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new IncreaseMaxDustAction(1));
        if(cardsToPreview!=null)
            addToBot(new MakeTempCardInHandAction(cardsToPreview));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            this.isEthereal = true;
        }
    }
}







package Theresa.card.skill;

import Theresa.action.OnlyForYouAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class OnlyForYou extends AbstractTheresaCard {
    public static final String ID = "theresa:OnlyForYou";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public OnlyForYou() {
        super(ID, cardStrings.NAME, 1, cardStrings.DESCRIPTION, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new OnlyForYouAction());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}


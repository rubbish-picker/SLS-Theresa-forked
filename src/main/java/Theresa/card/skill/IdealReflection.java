package Theresa.card.skill;

import Theresa.action.IdealReflectionAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.OtherEnum;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IdealReflection extends AbstractTheresaCard {
    public static final String ID = "theresa:IdealReflection";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public IdealReflection() {
        super(ID,cardStrings.NAME,-1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        this.tags.add(OtherEnum.Theresa_Darkness);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new IdealReflectionAction(abstractPlayer,freeToPlayOnce,energyOnUse,upgraded));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}





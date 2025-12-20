package Theresa.card.attack;

import Theresa.action.BeforeDustAction;
import Theresa.action.IncreaseMagicNumberAction;
import Theresa.action.TheresaAttackAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BeforeDust extends AbstractTheresaCard {
    public static final String ID = "theresa:BeforeDust";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public BeforeDust() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        baseDamage = damage = 12;
        baseMagicNumber = magicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new TheresaAttackAction(true));
        int randomNumber = com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng.random(0, this.damage*2);
        addToBot(new DrawCardAction(magicNumber,new BeforeDustAction(magicNumber,abstractMonster,randomNumber,damageTypeForTurn,skillEffect),true));
        addToBot(new IncreaseMagicNumberAction(this,AbstractDungeon.cardRandomRng.random(0, 2)));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}

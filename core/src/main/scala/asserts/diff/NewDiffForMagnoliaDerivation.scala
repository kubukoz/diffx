package asserts.diff

import magnolia._

import scala.language.experimental.macros

trait NewDiffForMagnoliaDerivation {
  type Typeclass[T] = NewDiffFor[T]

  def combine[T](ctx: CaseClass[NewDiffFor, T]): NewDiffFor[T] = {
    val fields = ctx.parameters.map { p =>
      new FieldDiff[T, p.PType](p.label, { t: T =>
        p.dereference(t)
      }, p.typeclass)
    }
    new ObjNewDiffFor[T](ctx.typeName.short, fields.toList, Strategy.Compare)
  }

  def dispatch[T](ctx: SealedTrait[NewDiffFor, T]): NewDiffFor[T] = {
    ???
  }

  implicit def gen[T]: NewDiffFor[T] = macro Magnolia.gen[T]
}
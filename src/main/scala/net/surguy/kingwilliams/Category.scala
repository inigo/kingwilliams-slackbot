package net.surguy.kingwilliams

case class Category(number: Int, year: Int, preface: String, questions: List[Question])
case class Question(number: Int, text: String)


export default function NavigationComponent() {


    return (
        <div className=" bg-customColor2 flex items-center justify-between min-h-24 px-16">
            <div className="flex items-center">
                Logo
            </div>
            <ul className="flex items-center gap-x-16">
                <li>Профиль</li>
                <li>Курсы</li>
                <li>Расписание</li>
            </ul>
        </div>
    )
}
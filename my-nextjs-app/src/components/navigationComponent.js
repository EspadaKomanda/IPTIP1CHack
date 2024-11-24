"use client";
import Link from "next/link"
import {usePathname} from "next/navigation";
import { useEffect, useState } from "react";
import "../styles/navigationComponentStyles.css"
export default function NavigationComponent() {

    const pathname = usePathname();
    const [currentPath, setCurrentPath] = useState(pathname);

    useEffect(() => {
        setCurrentPath(pathname); 
    }, [pathname]);

    return (
        <div className=" bg-customColor2 flex items-center justify-between min-h-24 px-4 lg:px-16 text-customColor4 font-medium">
            <div className="flex items-center">
                Logo
            </div>
            <ul className="flex items-center">
                <li><Link href="/profile" className={currentPath === "/profile" ? "active rounded-full py-4 md:px-8 px-5" : "hover:text-customColor6 sm:py-4 sm:px-8 px-2"}>Профиль</Link></li>
                <li><Link href="/courses" className={currentPath === "/courses" ? "active rounded-full py-4 md:px-8 px-5" : "hover:text-customColor6 sm:py-4 sm:px-8 px-2"}>Курсы</Link></li>
                <li><Link href="/schedule" className={currentPath === "/schedule" ? "active rounded-full py-4 md:px-8 px-5" : "hover:text-customColor6 sm:py-4 sm:px-8 px-2"}>Расписание</Link></li>
            </ul>
        </div>
    )
}
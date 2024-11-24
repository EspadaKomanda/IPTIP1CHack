"use client";
import NavigationComponent from "@/components/navigationComponent";
import Image from "next/image";
import { useState, useRef } from "react";


export default function Profile() {

    const [avatar, setAvatar] = useState('/default.png');
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isDragging, setIsDragging] = useState(false);
    const [selectedFile, setSelectedFile] = useState(null);
    const fileInputRef = useRef(null);

    const handleAvatarChange = (file) => {
        const reader = new FileReader();
        reader.onloadend = () => {
            setAvatar(reader.result);
            setIsModalOpen(false);
        };
        reader.readAsDataURL(file);
    };

    const handleFileInputClick = () => {
        fileInputRef.current.click();
    };

    const handleDrop = (event) => {
        event.preventDefault();
        setIsDragging(false);
        const file = event.dataTransfer.files[0];
        if (file) {
            handleAvatarChange(file);
        }
    };

    const handleDragOver = (event) => {
        event.preventDefault();
        setIsDragging(true);
    };

    const handleDragLeave = () => {
        setIsDragging(false);
    };

    return (
        <>
            <header className=" mb-16">
                    <NavigationComponent/>
            </header>
            <main>
                <div className="container grid lg:grid-cols-2 grid-rows-[auto_1fr_auto] gap-9 font-semibold p-4">
                    <div className="">
                        <p className="text-right text-sm pr-4 font-medium text-customColor4">личная информация</p>
                        <div className="bg-customColor1 rounded-2xl p-9 sm:flex ">
                            <Image 
                                src={avatar} 
                                alt="" 
                                width={130} 
                                height={130} 
                                className="rounded-full border-2 border-customColor1 bg-customColor8 cursor-pointer mr-9 mb-4 h-32" 
                                onClick={() => setIsModalOpen(true)}
                            />
                            <div className="gap-y-9 flex flex-col">
                                <p className="text-customColor7 text-xl">Имя Фамилия Отчество</p>
                                <p>Дата рождения: <span className="text-customColor4"></span></p>
                                <p>№ студенческого: <span className="text-customColor4"></span></p>
                            </div>
                        </div>
                    </div>
                    <div className="row-span-2">
                        <p className="text-right text-sm pr-4 font-medium text-customColor4">роль в институте</p>
                        <div className=" bg-customColor1 rounded-2xl p-9 pb-16 gap-y-9 flex flex-col">  
                            <p>Направление: <span className="text-customColor4"></span></p>
                            <p>Код программы: <span className="text-customColor4"></span></p> 
                            <p>Институт: <span className="text-customColor4"></span></p> 
                            <p>Профиль: <span className="text-customColor4"></span></p> 
                            <p>Курс: <span className="text-customColor4"></span></p> 
                            <p>Группа: <span className="text-customColor4"></span></p> 
                        </div>
                    </div>
                    <div>
                        <p className="text-right text-sm pr-4 font-medium text-customColor4">контакты</p>
                        <div className=" bg-customColor1 rounded-2xl p-9 gap-y-9 flex flex-col"> 
                            <p>Телефон: <span className="text-customColor4"></span></p>
                            <p>Email: <span className="text-customColor4"></span></p> 
                        </div>
                    </div>
                </div>
            </main>
            {isModalOpen && (
                <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-30">
                    <div className="bg-white p-5 rounded-2xl shadow-lg">
                        <h2 className="text-lg font-bold mb-4">Редактирование фотографии</h2>
                        <div 
                            className={`border-2 p-4 text-center mb-4 cursor-pointer ${isDragging ? 'rounded-lg bg-blue-100' : 'rounded-lg border-dashed border-gray-300'}`}
                            onClick={handleFileInputClick}
                            onDrop={handleDrop}
                            onDragOver={handleDragOver}
                            onDragLeave={handleDragLeave}
                        >
                            Перетащите изображение сюда или нажмите, чтобы загрузить
                        </div>
                        <input 
                            type="file" 
                            accept="image/*" 
                            onChange={(e) => handleAvatarChange(e.target.files[0])} 
                            ref={fileInputRef}
                            className="hidden"
                        />
                        <button 
                            className="mt-4 bg-customColor6 text-white px-4 py-2 rounded" 
                            onClick={() => setIsModalOpen(false)}
                        >
                            Закрыть
                        </button>
                    </div>
                </div>
            )}
        </>
    );
}
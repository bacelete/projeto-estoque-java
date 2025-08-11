'use client'
import { useState } from "react";
import { useEffect } from "react";
import { useRouter } from 'next/navigation'

export default function Home() {
    const router = useRouter();

    return (
        <>
            <h1 className="text-black text-lg p-2">Deu certo!</h1>
        </>
    )
}